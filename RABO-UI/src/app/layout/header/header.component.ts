import {Component, OnInit} from '@angular/core';
import {Router, RouterEvent} from '@angular/router';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  activeMenu = '';

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.activeMenu = this.router.url;
    console.log('this.activeMenu - ', this.activeMenu);
    this.router.events.pipe(filter(e => e instanceof RouterEvent)
    ).subscribe((e: any) => {
      this.activeMenu = e.url;
      console.log(e.id, e.url);
    });
  }

  navigate(s: string) {

    this.router.navigateByUrl(s);
  }
}
