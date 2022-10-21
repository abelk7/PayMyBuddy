import { Component, OnInit, AfterViewInit } from '@angular/core';
declare var $: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userIsLogged : Boolean = false;

  constructor() { }

  ngOnInit(): void {
    //Active menu
    $('#navmenu .navbar-nav a' ).on( 'click', function () {
      $( '#navmenu .navbar-nav' ).find( 'li.active' ).removeClass( 'active' );
      $(this).parent( 'li' ).addClass( 'active' );
    });

  }

  logout() {
  }

}
