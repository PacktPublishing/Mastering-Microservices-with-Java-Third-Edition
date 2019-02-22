import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterStateSnapshot } from '@angular/router';

import { faCalendar } from '@fortawesome/free-regular-svg-icons';
import { RestService } from '../rest.service';

@Component({
  selector: 'mmj-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss']
})
export class RestaurantComponent implements OnInit {
  faCalendar = faCalendar;
  datepickerModel: Date;
  bookingDate: Date = new Date();
  tm: Date;
  minDate: Date = new Date();
  maxDate: Date = new Date();
  id$: any;
  restaurant$: any = [];
  bookingInfo: any;
  bookingResponse: any = [];



  constructor(private router: Router, private route: ActivatedRoute,
    private restService: RestService) {
    this.route.params.subscribe(params => this.id$ = params.id);
    this.route.params.subscribe(params => this.bookingInfo = params.bookingInfo);
    // if (!this.bookingInfo) {
    //   this.bookingInfo.restaurant = this.restaurant$;
    //   this.bookingInfo.date = this.bookingDate;
    //   this.bookingInfo.time = this.tm;
    // }
    this.minDate.setDate(this.minDate.getDate() + 1);
    this.maxDate.setDate(this.maxDate.getDate() + 180);
    this.bookingDate.setDate(this.bookingDate.getDate() + 1);
    this.tm = this.bookingDate;
    this.tm.setHours(13, 0);
    this.bookingInfo = {
      bookingId: '',
      restaurantId: '',
      restaurant: this.restaurant$,
      userId: '',
      date: this.bookingDate,
      time: this.tm
    };
  }

  ngOnInit() {
    this.getRestaurant(this.id$);
  }

  getRestaurant(id) {
    this.restService.getRestaurant(id).subscribe(
      data => { this.restaurant$ = data; console.log(this.restaurant$); }
    )
  }

  book() {
    this.bookingInfo.restaurantId = this.restaurant$.id;
    this.bookingInfo.userId = this.restService.currentUser;
    this.bookingInfo.date = this.bookingDate;
    this.bookingInfo.time = this.tm;
    console.log('reserving table...');
    this.restService.performBooking(this.bookingInfo).subscribe(
      data => {
        this.bookingResponse = data; console.log('data' + JSON.stringify(data));
        console.log("Booking confirmed with id --> " + this.bookingResponse.data.id);
      }
    )
    alert("Booking Confirmed!!!\nRedirecting back to home page.");
    this.router.navigate(['/']);
  }

}
