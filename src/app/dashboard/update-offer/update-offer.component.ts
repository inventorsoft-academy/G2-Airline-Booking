import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpOfferService} from '../../common/services/http.offer.service';
import {Subscription} from 'rxjs/Subscription';
import {Offer} from "../../common/models/offer/offer";
import {ActivatedRoute} from "@angular/router";

@Component({
  templateUrl: './update-offer.component.html'
})
export class UpdateOfferComponent implements OnInit, OnDestroy {



  newOfferForm = this.fb.group({
    departureCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"),Validators.required]],
    arrivalCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}") , Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    arrivalDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    numberOfSeats: ['', [Validators.required, Validators.min(50)]],
    price: ['', [Validators.required, Validators.min(80)]]
  });

  subscriptions: Subscription[] = [];

  offer: Offer;

  constructor(private fb: FormBuilder,
              private httpService: HttpOfferService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.findOfferById(this.route.snapshot.params.id);

  }

  findOfferById(id) {
    let findOfferSubs = this.httpService.findOfferById(id)
      .subscribe(
        res => {
          this.offer = res;
          this.initializationForm();
        }
      );
    this.subscriptions.push(findOfferSubs);
  }

  initializationForm() {
    this.newOfferForm.patchValue({departureCity : this.offer.departureCity});
    this.newOfferForm.patchValue({arrivalCity : this.offer.arrivalCity});
    this.newOfferForm.patchValue({departureDate : this.offer.departureDate});
    this.newOfferForm.patchValue({arrivalDate : this.offer.arrivalDate});
    this.newOfferForm.patchValue({numberOfSeats : this.offer.numberOfSeats});
    this.newOfferForm.patchValue({price: this.offer.price});
  }

  updateOffer() {
    let updateOffersSubscription = this.httpService.updateOffer(this.newOfferForm.value)
      .subscribe(
        res => console.log(res),
        err => console.log(err));
    this.subscriptions.push(updateOffersSubscription);
    this.clearForm();
  }

  clearForm() {
    this.newOfferForm.reset();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      subscription => {
        subscription.unsubscribe();
      }
    )
  }
}
