import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {MainPageComponent} from "./dashboard/main-page/main-page.component";
import {OffersListComponentList} from "./dashboard/offers-list/offers-list.component";
import {OfferDetailsComponent} from "./dashboard/offer-details/offer-details.component";
import {NewOfferComponent} from "./dashboard/new-offer/new-offer.component";
import {DashboardModule} from "./dashboard/dashboard.module";
import {RouterModule} from "@angular/router";
import {HttpOfferService} from "./common/services/http.offer.service";
import {BalanceComponent} from "./dashboard/actions/admin-actions/balance-of-bought-tickets/balance.component";
import {HttpBalanceService} from "./common/services/http.balance.service";
import {UpdateOfferComponent} from "./dashboard/update-offer/update-offer.component";

const routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: '',
        redirectTo: 'main-page',
        pathMatch: 'full'
      },
      {
        path: 'main-page',
        component: MainPageComponent
      },
      {
        path: 'offers-list',
        component: OffersListComponentList
      },
      {
        path: 'update-offer/:id',
        component: UpdateOfferComponent
      },
      {
        path: 'balance-of-bought-ticket',
        component: BalanceComponent
      },
      {
        path: 'offer-details/:id',
        component: OfferDetailsComponent
      },
      {
        path: 'new-offer',
        component: NewOfferComponent
      }
    ]
  },
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  }
];


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    DashboardModule,
    RouterModule.forRoot(routes)
  ],
  providers: [HttpOfferService, HttpBalanceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
