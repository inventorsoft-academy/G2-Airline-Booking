import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {HttpOfferService} from '../../common/services/http.offer.service';
import {Subscription} from 'rxjs/Subscription';

@Component({
  templateUrl: './new-offer.component.html'
})
export class NewOfferComponent implements OnInit, OnDestroy {

  newOfferForm = this.fb.group({
    departureCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"),Validators.required]],
    arrivalCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}") , Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    arrivalDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    numberOfSeats: ['', [Validators.required, Validators.min(50)]],
    price: ['', [Validators.required, Validators.min(80)]]
  });

  subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private httpService: HttpOfferService) {
  }

  ngOnInit() {
  }

  saveOffer() {
    let saveOfferSubs = this.httpService.saveOffer(this.newOfferForm.value).subscribe();
    this.subscriptions.push(saveOfferSubs);
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
