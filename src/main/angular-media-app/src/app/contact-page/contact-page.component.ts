import { Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';
import { UserService } from '../services/user.service';
import { User } from '../models/user';
import { Address } from '../models/address';

@Component({
  selector: 'app-contact-page',
  templateUrl: './contact-page.component.html',
  styleUrl: './contact-page.component.css'
})
export class ContactPageComponent implements OnInit{
  user: User = JSON.parse(sessionStorage.getItem("current-user") ?? "");
  validEmailAddress: boolean = true;

  constructor(
    private addressService: AddressService,
    private userService: UserService
  ){
    
  }

  ngOnInit(): void {
    
  }

  addressToString(address: Address): string{
    return address.country + ", " + address.zipCode + " " + address.city + ", " + address.street + " " + address.houseNum;
}

// getDate(): Date{
//   return new Date(this.user.birthdate);
// }


  updateUser(){
    this.userService.update(this.user.id, this.user);
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
