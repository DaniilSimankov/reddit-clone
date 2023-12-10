import { Component, OnInit } from '@angular/core';
import { SubredditModel } from 'app/subreddit/subreddit-response';
import { SubredditService } from 'app/subreddit/subreddit.service';

@Component({
  selector: 'app-subbreddit-side-bar',
  templateUrl: './subbreddit-side-bar.component.html',
  styleUrls: ['./subbreddit-side-bar.component.css']
})
export class SubbredditSideBarComponent implements OnInit {

  subreddits: Array<SubredditModel>;
  displayViewAll: boolean;

  constructor(private subredditSerice: SubredditService) {
    this.subredditSerice.getAllSubreddits().subscribe(data =>{
      if(data.length >= 4){
        this.subreddits = data.slice(0,3);
        this.displayViewAll = true;
      }else{
        this.subreddits = data;
      }
    });
   }

  ngOnInit(): void {
  }

}
