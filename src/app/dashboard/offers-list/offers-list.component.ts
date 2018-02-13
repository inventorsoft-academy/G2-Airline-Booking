import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpService } from '../../common/services/http.service';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';
import {Offer} from "../../common/models/offers/offer";

@Component({
    templateUrl: './offers-list.component.html',
    styleUrls: ['./offers-list.component.css']
})
export class OffersListComponentList implements OnInit, OnDestroy {


    offersList: Offer[];

    subscriptions: Subscription[] = [];

    constructor(private httpService: HttpService,
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
