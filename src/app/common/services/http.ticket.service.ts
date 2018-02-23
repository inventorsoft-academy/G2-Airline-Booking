import {Http, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Ticket} from "../models/ticket/ticket";
import {environment} from "../../../environments/environment";

@Injectable()
export class HttpTicketService {

  constructor(private http: Http) {
  }


  getBalance(): Observable<any> {
    return this.http.get(environment.API + '/tickets/balance').map(res => res.json());
  }

  download (offerId: number, customerId: number) : Observable<Response> {
    return this.http.get(environment.API + '/tickets/' +
      '?offerId='+ offerId +
      '&customerId=' + customerId);
  }

}
