import { Component, OnInit } from '@angular/core';
import { ChartType,ChartData } from 'chart.js';
import { DashboardService } from 'src/app/Dashboard-Service/dashboard.service';

@Component({
  selector: 'city-movie-chart',
  templateUrl: './city-movie-chart.html',
  styleUrls: ['./city-movie-chart.css']
})
export class LocationChartComponent implements OnInit {

  showData:boolean = false;
  public barChartType: ChartType = 'bar';
  public barChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [
      { data: [], label: 'Movies' }
    ]
  };

  constructor(private dashboardService:DashboardService){}
  ngOnInit(): void {
    this.dashboardService.getDashboardData('locationchart').subscribe(locationChardData=>{
      var labels:string[] = []
      var data:Array<number> = [];
      locationChardData?.chartDataList.forEach((chartData: {
          chartValue: number; chartLable: string; 
        })=>{
        labels.push(chartData?.chartLable);
        data.push(chartData?.chartValue);
      });
      this.barChartData.labels = labels;
      this.barChartData.datasets[0].data = data;
      this.showData = true;
    })
}
}