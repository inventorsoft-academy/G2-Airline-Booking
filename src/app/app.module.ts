import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {MainPageComponent} from "./dashboard/main-page/main-page.component";
import {OffersListComponentList} from "./dashboard/offers-list/offers-list.component";
import {OfferDetailsComponent} from "./dashboard/offer-details/offer-details.component";
import {NewOfferComponent} from "./dashboard/new-offer/new-offer.component";
import {DashboardModule} from "./dashboard/dashboard.module";
import {RouterModule} from "@angular/router";
import {HttpOfferService} from "./common/services/http.offer.service";
import {BalanceComponent} from "./dashboard/balance-of-bought-tickets/balance.component";
import {HttpBalanceService} from "./common/services/http.balance.service";
import {UpdateOfferComponent} from "./dashboard/update-offer/update-offer.component";
import {SearchByParameterComponent} from "./dashboard/search-by-parameter/search-by-parameter.component";
import {AdminLogComponent} from "./dashboard/admin-log/admin-log.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatFormFieldModule, MatOptionModule,
  MatSelectModule
} from "@angular/material";
import {AdminLogModule} from "./dashboard/admin-log/admin-log.module";
import {HttpTicketService} from "./common/services/http.ticket.service";
import {FormsModule} from "@angular/forms";

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
        path: 'search-by-parameter',
        component: SearchByParameterComponent
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
      },
      {
        path: 'admin-log',
        component: AdminLogComponent
      },
      {
        path: 'balance-of-bought-tickets',
        component: BalanceComponent
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
    RouterModule.forRoot(routes),
  ],
  providers: [HttpOfferService, HttpTicketService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
