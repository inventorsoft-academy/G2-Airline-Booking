/*
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpService } from '../../common/services/http.offer.service';
import { Subscription } from 'rxjs/Subscription';

@Component({
    templateUrl: './new-offer.component.html'
})
export class NewOfferComponent implements OnInit, OnDestroy {

    newOfferForm = this.fb.group({
        route:['', Validators.required],
        departureDate: ['', Validators.required],
        arrivalDate: ['', Validators.required],
        numberOfSeats: ['', Validators.required],
        price: [[''], Validators.required]
    });

    subscriptions: Subscription[] = [];

    constructor(private fb: FormBuilder,
                private httpService: HttpService) {
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
*/
