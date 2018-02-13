import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../../common/services/http.service';
import { Subscription } from 'rxjs/Subscription';
import {Offer} from "../../common/models/offers/offer";

@Component({
    templateUrl: './offer-details.component.html'
})
export class OfferDetailsComponent implements OnInit, OnDestroy {

    offer: Offer;

    subscriptions: Subscription[] = [];

    constructor(private route: ActivatedRoute,
                private httpService: HttpService) {
    }

    ngOnInit() {
        this.findOfferById(this.route.snapshot.params.id);
    }

    findOfferById(id) {
        let findOfferSubs = this.httpService.findOfferById(id)
            .subscribe(
                res => {
                    this.offer = res;
                }
            );
        this.subscriptions.push(findOfferSubs);
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(
            subscription => {
                subscription.unsubscribe();
            }
        )
    }
}
