import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { Offer } from '../models/offers/offer';
import { environment } from '../../../environments/environment';

@Injectable()
export class HttpService {

    constructor(private http: Http) {
    }

    getOffers(): Observable<Offer[]> {
        return this.http.get(environment.API + '/offers').map(res => res.json());
    }

    findOfferById(id): Observable<Offer> {
        return this.http.get(environment.API + `/offers/${id}`).map(res => res.json());
    }

    saveOffer(obj: Offer): Observable<Response> {
        return this.http.post(environment.API + '/offers', obj);
    }

}
