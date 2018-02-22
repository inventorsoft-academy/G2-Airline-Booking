import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {HttpTicketService} from "../../common/services/http.ticket.service";

@Component({
  templateUrl: './balance.component.html'
})
export class BalanceComponent implements OnInit, OnDestroy {

  balance: any;

  subscriptions: Subscription[] = [];

  constructor(private httpService: HttpTicketService) {
  }

  ngOnInit() {
    this.getBalance();
  }

  getBalance() {
    let getOffersSubscription = this.httpService.getBalance()
      .subscribe(
        res => {
          this.balance = res;
        }
      );
    this.subscriptions.push(getOffersSubscription);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    })
  }

}
