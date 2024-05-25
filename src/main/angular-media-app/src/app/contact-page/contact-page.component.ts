import { Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';
import { UserService } from '../services/user.service';
import { User } from '../models/user';
import { Address } from '../models/address';
import { ActivatedRoute, Route } from '@angular/router';

@Component({
  selector: 'app-contact-page',
  templateUrl: './contact-page.component.html',
  styleUrl: './contact-page.component.css'
})
export class ContactPageComponent implements OnInit{
  userIdFromUrl: string | null = this.route.snapshot.paramMap.get("userId");
  user: User = new User();
  currentUser: User = JSON.parse(sessionStorage.getItem("current-user") ?? '');
  validEmailAddress: boolean = true;
  

  constructor(
    private addressService: AddressService,
    private userService: UserService,
    private route: ActivatedRoute
  ){
    this.userService.getById(Number(this.userIdFromUrl)).subscribe(resp => this.user = resp)
  }

  ngOnInit(): void {
    
  }

  addressToString(address: Address): string{
    return address.country + ", " + address.zipCode + " " + address.city + ", " + address.street + " " + address.houseNum;
}

  isOwnUserPage(): boolean{
    return this.currentUser.id === this.user.id;
  }

  updateUser(){
    console.log(this.user.email)
    this.userService.update(this.user.id, this.user).subscribe();
  }

  isValidName(name: string): boolean{
    let nameRegex: RegExp = /^[\p{L}\p{M} ]+$/u;
    if(!nameRegex.test(name)){
      return false;
    }
    return true;
  }

  isValidEmail(): boolean{
    let email: string = this.user.email;
    let emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if(!emailRegex.test(email)){
      return false;
    }
    return true;
  }

  isAllValid(): boolean{
    return this.isValidName(this.user.firstName)
    && this.isValidName(this.user.lastName)
    && this.isValidEmail();
  }
}
