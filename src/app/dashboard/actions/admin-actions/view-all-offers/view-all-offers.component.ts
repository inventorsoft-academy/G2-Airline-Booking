import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpOfferService } from '../../../../common/services/http.offer.service';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';
import {Offer} from "../../../../common/models/offer/offer";

@Component({
    templateUrl: './view-all-offers.component.html',
    styleUrls: ['./view-all-offers.component.css']
})
export class OffersListComponentList implements OnInit, OnDestroy {


    offersList: Offer[];

    subscriptions: Subscription[] = [];

    constructor(private httpService: HttpOfferService,
                private router: Router) {
    }

    ngOnInit() {
        this.getOffers();
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

    showOfferDetails(id) {
        this.router.navigate([`dashboard/offer-details/${id}`]);
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        })
    }
}
