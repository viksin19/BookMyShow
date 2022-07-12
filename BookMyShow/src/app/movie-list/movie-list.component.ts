import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { MovieList } from '../modal-interfaces/movieList.model';
import { MovieService } from '../MovieServices/movie-service.service';
import { MovieDetailsDailog } from './movie-details-dailogue/movie-details.component';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit,AfterViewInit {

  displayedColumns: string[] = ['Name', 'Cast', 'Languages', 'Genre','Locations','View details'];
  movieTableData:MovieList[] = [];
  isMovieData:boolean = false;
  dataSource = new MatTableDataSource<MovieList>(this.movieTableData);

  @ViewChild(MatPaginator) paginator: MatPaginator|any;
  constructor(private movieService:MovieService,public dialog: MatDialog) { }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.initMovieTableData();
  }
  initMovieTableData():void {
    this.movieService.getMovieData('tabledata').subscribe(movieTable=>{
      this.movieTableData = movieTable;
      this.dataSource = new MatTableDataSource<MovieList>(this.movieTableData);
      this.dataSource.paginator = this.paginator;
      this.isMovieData = true;
    });
  }

  viewDetails(movie:MovieList){
    //console.log(movie);
    this.dialog.open(MovieDetailsDailog,{
      data:movie
    });
  }

}
