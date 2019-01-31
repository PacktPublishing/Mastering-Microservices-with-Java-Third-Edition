import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'mmj-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.scss']
})
export class RestaurantsComponent implements OnInit {

  searchValue: string;
  restaurants$: any = [];

  constructor(private restService: RestService) { }

  ngOnInit() {
    this.getRestaurants();
  }

  getRestaurants() {
    this.restService.getRestaurants().subscribe(
      data => this.restaurants$ = data
    )
  }
  
  searchRestaurants(value: string) {
    this.searchValue = value;
    this.restService.searchRestaurants(value).subscribe(
      data => this.restaurants$ = data
    )
  }
}
