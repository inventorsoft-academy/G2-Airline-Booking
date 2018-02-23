import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpModule} from '@angular/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {HeaderComponent} from './header/header.component';
import {MainPageComponent} from './main-page/main-page.component';
import {NewOfferComponent} from './new-offer/new-offer.component';
import {OffersListComponentList} from './offers-list/offers-list.component';
import {OfferDetailsComponent} from './offer-details/offer-details.component';
import {DashboardComponent} from './dashboard.component';
import {BalanceComponent} from "./balance-of-bought-tickets/balance.component";
import {UpdateOfferComponent} from "./update-offer/update-offer.component";
import {SearchByParameterComponent} from "./search-by-parameter/search-by-parameter.component";
import {AdminLogComponent} from "./admin-log/admin-log.component";
import {AdminLogModule} from "./admin-log/admin-log.module";

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    RouterModule,
    ReactiveFormsModule,
    AdminLogModule,
    FormsModule
  ],
  declarations: [
    HeaderComponent,
    MainPageComponent,
    NewOfferComponent,
    UpdateOfferComponent,
    OffersListComponentList,
    OfferDetailsComponent,
    SearchByParameterComponent,
    DashboardComponent,
    BalanceComponent
  ],
  providers: [],
})
export class DashboardModule {
}
