import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpOfferService} from '../../common/services/http.offer.service';
import {Subscription} from 'rxjs/Subscription';
import {Router} from '@angular/router';
import {Offer} from "../../common/models/offer/offer";
import {FormBuilder, Validators} from "@angular/forms";
import {Ticket} from "../../common/models/ticket/ticket";
import {HttpTicketService} from "../../common/services/http.ticket.service";

@Component({
  templateUrl: './search-by-parameter.component.html',
  styleUrls: ['./search-by-parameter.component.css']
})
export class SearchByParameterComponent implements OnInit, OnDestroy {

  newOfferForm = this.offerBuilder.group({
    id: [, []],
    departureCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"), Validators.required]],
    arrivalCity: ['', [Validators.pattern("[A-ZА-Я]{3,4}"), Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    arrivalDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    numberOfSeats: ['', [Validators.required, Validators.min(50)]],
    price: ['', [Validators.required, Validators.min(80)]]
  });

  newFindForm = this.searchBuilder.group({
    departureCity: ['', [Validators.pattern("[А-Яа-я'\\-,]{2,40}"), Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
  });

  newBookedTickedForm = this.bookedBuilder.group({
    name: ['', [Validators.required]],
    departureCity: ['', [Validators.required]],
    arrivalCity: ['', [ Validators.required]],
    departureDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    arrivalDate: ['', [Validators.pattern("\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}"), Validators.required]],
    numberOfSeat: ['', [Validators.required, Validators.min(1)]],
    price: ['', [Validators.required, Validators.min(80)]]
  });

  offersList: Offer[];

  searchOffersList: Offer[];

  subscriptions: Subscription[] = [];

  customerId : string = '';

  numberOfSeat : string = '';

  ticket: Ticket;

  constructor(private httpOfferService: HttpOfferService,
              private httpTicketService: HttpTicketService,
              private offerBuilder: FormBuilder,
              private searchBuilder: FormBuilder,
              private bookedBuilder: FormBuilder,
              private router: Router) {
  }

  ngOnInit() {
    this.getOffers();
    this.newOfferForm.markAsTouched();
  }

  getOffers() {
    let getOffersSubscription = this.httpOfferService.getOffersForCustomer()
      .subscribe(
        res => {
          this.offersList = res;
        }
      );
    this.subscriptions.push(getOffersSubscription);
  }

  search() {
    console.log(this.newFindForm.value.departureDate);
    console.log(this.newFindForm.value.departureCity);
    let deleteOffersSubscription = this.httpOfferService.searchOffers(this.newFindForm.value.departureCity, this.newFindForm.value.departureDate)
      .subscribe(
        res => {
          this.searchOffersList = res;
          console.log(this.searchOffersList);
        },
        err => console.log(err));
    this.subscriptions.push(deleteOffersSubscription);
  }

  bookATicket(offerId: string) {
    let deleteOffersSubscription = this.httpOfferService.bookATicket(offerId, this.customerId, this.numberOfSeat)
      .subscribe(
        res => {
          this.ticket = res;
          console.log(this.ticket);
          this.initializationForm();
          this.newOfferForm.reset();
          this.newFindForm.reset();
        }
  );
    this.subscriptions.push(deleteOffersSubscription);
  }

  initializationForm() {
    this.newBookedTickedForm.patchValue({name: this.ticket.name});
    this.newBookedTickedForm.patchValue({departureCity: this.ticket.departureCity});
    this.newBookedTickedForm.patchValue({arrivalCity: this.ticket.arrivalCity});
    this.newBookedTickedForm.patchValue({departureDate: this.ticket.departureDate});
    this.newBookedTickedForm.patchValue({arrivalDate: this.ticket.arrivalDate});
    this.newBookedTickedForm.patchValue({numberOfSeat: this.ticket.number});
    this.newBookedTickedForm.patchValue({price: this.ticket.price});
  }

  download() {
    let deleteOffersSubscription = this.httpTicketService.download(this.ticket.offerId, this.ticket.customerId)
      .subscribe(
        res => {
          console.log(res);
          this.newBookedTickedForm.reset();
          this.newOfferForm.reset();
          this.newFindForm.reset();
        }
      );
    this.subscriptions.push(deleteOffersSubscription);
  }

  showOfferDetails(id) {
    this.router.navigate([`dashboard/offer-details/${id}`]);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    })
  }
}
