import { Component, OnInit } from '@angular/core';
import { MovieService } from '../MovieServices/movie-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  recomendedMovieList:RecomendedMovieList[] = [];
  constructor(private service:MovieService) { }

  ngOnInit(): void {
  this.service.getMovieData('landingdata').subscribe(movieList=>{
    console.log(movieList);
    this.recomendedMovieList = movieList;
  });
  }

}
export interface RecomendedMovieList{
   movieId:number;
   movieName:string;
   movieGenre:Array<string>;
   imgUrl:string;
   movieLikes:number;
   movieRating:number;
}
