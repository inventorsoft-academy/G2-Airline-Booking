import {Component, OnDestroy, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Subscription} from "rxjs/Subscription";
import {HttpBalanceService} from "../../../../common/services/http.balance.service";

@Component({
  templateUrl: './balance.component.html'
})
export class BalanceComponent implements OnInit, OnDestroy {

  balance: any;

  subscriptions: Subscription[] = [];

  constructor(private httpService: HttpBalanceService,
              private router: Router) {
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
