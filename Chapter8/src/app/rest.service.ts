import { Injectable, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class RestService {
    private BASE_URL = 'https://localhost:8765/api/v1';
    // private loggedIn = new BehaviorSubject<boolean>(false);
    currentUser: any = null;// = new BehaviorSubject<any>({});
    private mockdata: any = [];

    constructor( private http: HttpClient, private router: Router) {
        this.mockdata.push(
            {
                id: "1",
                name: 'Le Meurice',
                address: '228 rue de Rivoli, 75001, Paris'
            });
        this.mockdata.push(
            {
                id: "2",
                name: 'L\'Ambroisie',
                address: '9 place des Vosges, 75004, Paris'
            });
        this.mockdata.push(
            {
                id: "3",
                name: 'Arpège',
                address: '84, rue de Varenne, 75007, Paris'
            });
        this.mockdata.push(
            {
                id: "4",
                name: 'Alain Ducasse au Plaza Athénée',
                address: '25 avenue de Montaigne, 75008, Paris'
            });
        this.mockdata.push(
            {
                id: "5",
                name: 'Pavillon LeDoyen',
                address: '1, avenue Dutuit, 75008, Paris'
            });
        this.mockdata.push(
            {
                id: "6",
                name: 'Pierre Gagnaire',
                address: '6, rue Balzac, 75008, Paris'
            });
        this.mockdata.push(
            {
                id: "7",
                name: 'L\'Astrance',
                address: '4, rue Beethoven, 75016, Paris'
            });
        this.mockdata.push(
            {
                id: "8",
                name: 'Pré Catelan',
                address: 'Bois de Boulogne, 75016, Paris'
            });
        this.mockdata.push(
            {
                id: "9",
                name: 'Guy Savoy',
                address: '18 rue Troyon, 75017, Paris'
            });
        this.mockdata.push(
            {
                id: "10",
                name: 'Le Bristol',
                address: '112, rue du Faubourg St Honoré, 8th arrondissement, Paris'
            });
        let currUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currUser) {
            this.currentUser= currUser;
        }
    }

    getRestaurants() {
        let mock = [this.mockdata];
        return this.http
            .get(this.BASE_URL + '/restaurants/') // 'https://jsonplaceholder.typicode.com/users'
            .pipe(catchError(
                function (error: HttpErrorResponse) {
                    console.log('Using mock data for fetching restaurants');
                    return mock;
                }
            ));
    }

    getRestaurant(id) {
        let mock = this.mockdata.filter(o => o.id === id);
        return this.http
            .get(this.BASE_URL + '/restaurants/' + id) // 'https://jsonplaceholder.typicode.com/users'
            .pipe(catchError(
                function (error: HttpErrorResponse) {
                    console.log('Using mock data for fetching a restaurant by id');
                    return mock;
                }
            ));
    }

    searchRestaurants(name) {
        let mock = [this.mockdata.filter(o => o.name.toLowerCase().startsWith(name.toLowerCase()) === true)];
        return this.http
            .get(this.BASE_URL + '/restaurants?name' + name) // 'https://jsonplaceholder.typicode.com/users'
            .pipe(catchError(
                function (error: HttpErrorResponse) {
                    console.log('Using mock data for search restaurants');
                    return mock;
                }
            ));
    }

    performBooking(bookingData) {
        console.log(JSON.stringify(bookingData));
        return this.http
            .post(this.BASE_URL + '/bookings/', bookingData)
            .pipe(catchError(
                function (error: HttpErrorResponse) {
                    console.log('Using mock data for booking');
                    let response = [{
                        data: { id: '999' },
                        status: 'success',
                        statusCode: 201
                    }]
                    return response;
                }
            ));
    }

    login(username: string, password: string) {
        return this.http
            .post<any>(this.BASE_URL + `/users/authenticate`, { username: username, password: password })
            .pipe(map(user => {
                if (user && user.token) {
                    this.currentUser = user;
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
                // this.loggedIn.next(true);
                return user;
            }))
            .pipe(catchError(
                function (error: HttpErrorResponse) {
                    console.log('Using mock data for Login');
                    console.log(this.mockdata);
                    this.currentUser = {
                        id: '99',
                        name: 'Roger'
                    };
                    let user = [this.currentUser];
                    localStorage.setItem('currentUser', JSON.stringify(this.currentUser));
                    return user;
                }
            ));
    }

    logout() {
        localStorage.removeItem('currentUser');
        this.currentUser = null;
        this.router.navigate(['/']);
    }

    // Workaround to update current user on navbar. Required only for mock currentUser update in error handling block
    updateCurrectUser() {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }
}
