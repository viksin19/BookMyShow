import { Component, OnInit } from '@angular/core';
import { ChartType,ChartData } from 'chart.js';
import { DashboardService } from 'src/app/Dashboard-Service/dashboard.service';

@Component({
  selector: 'genre-dashboard',
  templateUrl: './genre-dashboard.html',
  styleUrls: ['./genre-dashboard.css']
})
export class GenreDashboardComponent implements OnInit {

    showData:boolean = false;
  public doughnutChartLabels: string[] = [];
  public doughnutChartData: ChartData<'doughnut'> = {
    labels: this.doughnutChartLabels,
    datasets: [
      { data: [] }
    ]
  };
  public doughnutChartType: ChartType = 'doughnut';

  constructor(private dashboardService:DashboardService){}
  ngOnInit(): void {
    this.dashboardService.getDashboardData('genrechart').subscribe(genreChartData=>{
      console.log(genreChartData);
      var labels:string[] = []
      var data:Array<number> = [];
      genreChartData?.chartDataList.forEach((chartData: {
          chartValue: number; chartLable: string; 
        })=>{
        labels.push(chartData?.chartLable);
        data.push(chartData?.chartValue);
      });
      this.doughnutChartLabels = labels;
      console.log(this.doughnutChartLabels);
      this.doughnutChartData.labels = this.doughnutChartLabels;
      this.doughnutChartData.datasets[0].data = data;
      this.showData = true;
    });
}
}