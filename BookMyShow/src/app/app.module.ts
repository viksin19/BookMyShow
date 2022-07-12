import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from './material-module/material.module';
import { BrowserModule } from '@angular/platform-browser';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MovieListComponent } from './movie-list/movie-list.component';
import { HomeComponent } from './home/home.component';
import { MovieService } from './MovieServices/movie-service.service';
import { SignInDailogComponent } from './sign-in-dailog/sign-in-dailog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';
import { MovieDetailsDailog } from './movie-list/movie-details-dailogue/movie-details.component';
import { DashboardService } from './Dashboard-Service/dashboard.service';
import { HttpClientModule } from '@angular/common/http';
import { DashboardCard } from './dashboard/cards/dashboard-card';
import { ListToStringPipe } from './dashboard/pipes/listToString.pipe';
import { UserService } from './User/user.service';
import { GenreDashboardComponent } from './dashboard/genre-dashboard-chart/genre-dashboard.component';
import { LocationChartComponent } from './dashboard/city-movie-chart/city-movie-chart.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    MovieListComponent,
    HomeComponent,
    SignInDailogComponent,
    MovieDetailsDailog,
    DashboardCard,
    ListToStringPipe,
    GenreDashboardComponent,
    LocationChartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    NgChartsModule,
    HttpClientModule
  ],
  providers: [MovieService,DashboardService,UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
