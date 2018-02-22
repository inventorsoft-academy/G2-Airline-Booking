import {Http, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Ticket} from "../models/ticket/ticket";
import {environment} from "../../../environments/environment";

@Injectable()
export class HttpTicketService {

  constructor(private http: Http) {
  }

  getTickets(): Observable<Ticket[]> {
    return this.http.get(environment.API + '/tickets').map(res => res.json());
  }

  findTicketById(id): Observable<Ticket> {
    return this.http.get(environment.API + `/ticket/${id}`).map(res => res.json());
  }

  saveTicket(obj: Ticket): Observable<Response> {
    return this.http.post(environment.API + '/ticket', obj);
  }

  getBalance(): Observable<any> {
    return this.http.get(environment.API + '/tickets/balance').map(res => res.json());
  }



}
