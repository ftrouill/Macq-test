import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { HorseService } from '@app/_services';

@Component({ templateUrl: 'list.component.html' })
export class ListComponent implements OnInit {
    horses = null;

    constructor(private horseService: HorseService) {}

    ngOnInit() {
        this.horseService.getAll()
            .pipe(first())
            .subscribe(horses => this.horses = horses);
    }

    deleteHorse(id: string) {
        const horse = this.horses.find(x => x["_id"]["$oid"] === id);
        horse.isDeleting = true;
        this.horseService.delete(id)
            .pipe(first())
            .subscribe(() => this.horses = this.horses.filter(x => x["_id"]["$oid"] !== id));
    }
}