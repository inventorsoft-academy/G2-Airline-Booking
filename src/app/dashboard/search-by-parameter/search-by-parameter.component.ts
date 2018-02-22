import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpOfferService} from '../../common/services/http.offer.service';
import {Subscription} from 'rxjs/Subscription';
import {Router} from '@angular/router';
import {Offer} from "../../common/models/offer/offer";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  templateUrl: './search-by-parameter.component.html',
  styleUrls: ['./search-by-parameter.component.css']
})
export class SearchByParameterComponent implements OnInit, OnDestroy {

  newOfferForm = this.offerBuilder.group({
    id: [, []],
    departureCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"), Validators.required]],
    arrivalCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"), Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    arrivalDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    numberOfSeats: ['', [Validators.required, Validators.min(50)]],
    price: ['', [Validators.required, Validators.min(80)]]
  });

  newFindForm = this.searchBuilder.group({
    departureCity: ['', [Validators.pattern("[А-Яа-я]{2,40}"), Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
  });

  offersList: Offer[];

  subscriptions: Subscription[] = [];

  constructor(private httpService: HttpOfferService,
              private offerBuilder: FormBuilder,
              private searchBuilder: FormBuilder,
              private router: Router) {
  }

  ngOnInit() {
    this.getOffers();
    this.newOfferForm.markAsTouched();
  }

  getOffers() {
    let getOffersSubscription = this.httpService.getOffersForCustomer()
      .subscribe(
        res => {
          this.offersList = res;
        }
      );
    this.subscriptions.push(getOffersSubscription);
  }

  search() {
    console.log(this.newFindForm.value.departureDate);
    console.log(this.newFindForm.value.departureCity);
    let deleteOffersSubscription = this.httpService.searchOffers(this.newFindForm.value.departureCity, this.newFindForm.value.departureDate)
      .subscribe(
        res => {
          console.log(res);
        },
        err => console.log(err));
    this.subscriptions.push(deleteOffersSubscription);
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
