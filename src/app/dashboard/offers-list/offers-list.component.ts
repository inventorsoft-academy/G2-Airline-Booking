import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpOfferService} from '../../common/services/http.offer.service';
import {Subscription} from 'rxjs/Subscription';
import {Router} from '@angular/router';
import {Offer} from "../../common/models/offer/offer";
import index from "@angular/cli/lib/cli";
import {log} from "util";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  templateUrl: './offers-list.component.html',
  styleUrls: ['./offers-list.component.css']
})
export class OffersListComponentList implements OnInit, OnDestroy {

  newOfferForm = this.fb.group({
    id:[, []],
    departureCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"),Validators.required]],
    arrivalCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}") , Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    arrivalDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    numberOfSeats: ['', [Validators.required, Validators.min(50)]],
    price: ['', [Validators.required, Validators.min(80)]]
  });

  offersList: Offer[];

  subscriptions: Subscription[] = [];

  constructor(private httpService: HttpOfferService,
              private fb: FormBuilder,
              private router: Router) {
  }

  ngOnInit() {
    this.getOffers();
    this.newOfferForm.markAsTouched();
  }

  getOffers() {
    let getOffersSubscription = this.httpService.getOffers()
      .subscribe(
        res => {
          this.offersList = res;
        }
      );
    this.subscriptions.push(getOffersSubscription);
  }

  delete(offer: Offer) {
    let deleteOffersSubscription = this.httpService.deleteOffer(offer.id)
      .subscribe(
        res => {
          console.log(res);
          let index = this.offersList.indexOf(offer);
          if (index !== -1) {
            this.offersList.splice(index, 1);
          }
        },
        err => console.log(err));
    this.subscriptions.push(deleteOffersSubscription);
  }

  updateOffer(id) {
    this.router.navigate([`dashboard/update-offer/${id}`]);
  }

  showOfferDetails(id) {
    this.router.navigate([`dashboard/offer-details/${id}`]);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    })
  }
}
