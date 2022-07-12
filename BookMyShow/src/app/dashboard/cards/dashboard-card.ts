import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { DashboardService } from 'src/app/Dashboard-Service/dashboard.service';

@Component({
  selector: 'dashboard-card',
  templateUrl: './dashboard-card.html',
  styleUrls: ['../dashboard.component.css']
})
export class DashboardCard implements OnInit,OnChanges {

   // title:string='';
    apiCardInput:string = '';
    cardValue:number=0;
    showCardSubBody:boolean = true;
    cardSuBody:any[] = [];
    cardColor:string='info-card';
    showCard:boolean = false;

    @Input('titleinput') title:string = '';
    //     console.log(titleinput);
    //     this.title = titleinput; 
    //     this.apiCardInput = titleinput.toLowerCase();
    //     this.prepareCardData();
    // }

    ngOnChanges(changes: SimpleChanges): void {
        console.log(changes);
        if(this.title!=''){
        this.apiCardInput = this.title.toLowerCase();
         this.prepareCardData();
        }
    }
    constructor(private dashboardService:DashboardService){}

    prepareCardData() {
        if(this.title=='Movies')
        this.showCardSubBody = false;

        //this.initCardColor();

        this.cardValue = 0;
        this.cardSuBody = [];
        this.dashboardService.getDashboardData(this.apiCardInput).subscribe(cardResponse=>{
            this.cardValue = cardResponse.cardValue;
            this.cardSuBody = cardResponse.subTitle;
            this.showCard = true;
        });
      
    }
    initCardColor() {
       switch (this.title) {
           case 'Genre':
            this.cardColor = 'info-card';
            break;
           case 'Locations':
            this.cardColor = 'danger-card';
               break;
           case 'Languages':
            this.cardColor = 'orange-card';
               break;
           default:
               this.cardColor = 'blue-card';
               break;
       } 
    }
    ngOnInit(): void {
     
    }
}