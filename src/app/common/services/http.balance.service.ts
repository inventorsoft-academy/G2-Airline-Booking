import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {environment} from "../../../environments/environment";


@Injectable()
export class HttpBalanceService {

  constructor(private http: Http) {
  }

  getBalance(): Observable<any> {
    return this.http.get(environment.API + '/offers/balance').map(res => res.json());
  }

}
