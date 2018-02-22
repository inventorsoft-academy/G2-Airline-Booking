import {Injectable} from '@angular/core';
import {Http, RequestOptionsArgs, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {Offer} from '../models/offer/offer';
import {environment} from '../../../environments/environment';

@Injectable()
export class HttpOfferService {

  constructor(private http: Http) {
  }

  getOffers(): Observable<Offer[]> {
    return this.http.get(environment.API + '/offers').map(res => res.json());
  }

  getOffersForCustomer(): Observable<Offer[]> {
    return this.http.get(environment.API + '/offers/forCustomer').map(res => res.json());
  }

  searchOffers(departureCity: string, departureDate: string): Observable<Offer[]> {
    return this.http.get(environment.API + '/offers/search/' +
      '?departureCity='+ departureCity +
    '&departureDate=' + departureDate).map(res => res.json());
  }

  findOfferById(id): Observable<Offer> {
    return this.http.get(environment.API + '/offers/' + id).map(res => res.json());
  }

  saveOffer(obj: Offer): Observable<Response> {
    return this.http.post(environment.API + '/offers', obj);
  }

  updateOffer(obj: Offer): Observable<Response> {
    return this.http.put(environment.API + '/offers/' + obj.id, obj);
  }

  deleteOffer(id): Observable<Response> {
    return this.http.delete(environment.API + '/offers/' + id);
  }


}
