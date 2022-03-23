import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { environment } from '@environments/environment';
import { Horse } from '@app/_models';

@Injectable({ providedIn: 'root' })
export class HorseService {

    constructor(
        private http: HttpClient
    ) {

    }

    getAll() {
        return this.http.get<Horse[]>(`${environment.apiUrl}/horses`, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                "token": JSON.parse(localStorage.getItem('user')).token
            })
        });
    }

    getById(id: string) {
        return this.http.get<Horse>(`${environment.apiUrl}/horses/${id}`, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                "token": JSON.parse(localStorage.getItem('user')).token
            })
        })
    }

    add(horse: Horse) {
        return this.http.post(`${environment.apiUrl}/horses`, {
            "name": horse.name,
            "color": horse.color,
            "speed": horse.speed,
            "breed": horse.breed,
            "image": horse.image
        }, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                "token": JSON.parse(localStorage.getItem('user')).token
            })
        })
    }

    update(id, params) {
        return this.http.put(`${environment.apiUrl}/horses/${id}`, params, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                "token": JSON.parse(localStorage.getItem('user')).token
            })
        })

    }

    delete(id: string) {
        return this.http.delete(`${environment.apiUrl}/horses/${id}`, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                "token": JSON.parse(localStorage.getItem('user')).token
            })
        });
    }
}