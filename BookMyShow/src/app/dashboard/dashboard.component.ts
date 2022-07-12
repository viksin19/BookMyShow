import { Component, OnInit } from '@angular/core';
import { ChartType,ChartData } from 'chart.js';
import { DashboardService } from '../Dashboard-Service/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public doughnutChartLabels: string[] = [ 'Action', 'Adventure', 'Comedy','Drama','Thriller','Horor' ];
  public doughnutChartData: ChartData<'doughnut'> = {
    labels: this.doughnutChartLabels,
    datasets: [
      { data: [ 350, 450, 100, 78, 100 ] }
    ]
  };
  public doughnutChartType: ChartType = 'doughnut';
  // doughnut chart ends

  public barChartType: ChartType = 'bar';
  public barChartData: ChartData<'bar'> = {
    labels: [ 'Bengaluru', 'Delhi', 'Mumbai', 'Hydarabad', 'Varanasi', ],
    datasets: [
      { data: [ 8, 7, 10, 5, 4, ], label: 'Series A' }
    ]
  };
  cardTitles:any[]=[
    {title:'Movies',cardClass:'blue-card'},
    {title:'Genre',cardClass:'info-card'},
    {title:'Languages',cardClass:'orange-card'},{title:'Locations',cardClass:'danger-card'}];
  constructor(private dashboardService:DashboardService) { }

  ngOnInit(): void {
  }

}
