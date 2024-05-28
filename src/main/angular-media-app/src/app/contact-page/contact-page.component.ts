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
  currentUser: User = JSON.parse(sessionStorage.getItem("current-user") ??'');
  validEmailAddress: boolean = true;
  selectedFile: string = "";
  uiEditMode: boolean = false;
  uiCreationMode: boolean = false;
  addressToBeEdited: Address = new Address();


  constructor(
    private addressService: AddressService,
    private userService: UserService,
    private route: ActivatedRoute

  ){
    this.userService.getById(Number(this.userIdFromUrl)).subscribe(resp => {
      this.user = User.convertNewUser(resp);
      console.log(this.user.firstName);
    });
    // this.userService.getByIdSync(Number(sessionStorage.getItem("current-user-id") ?? '')).then(resp => this.currentUser = resp);
  }

  ngOnInit(): void {

  }

  addressToString(address: Address): string{
    return address.country + ", " + address.zipCode + " " + address.city + ", " + address.street + " " + address.houseNum;
  }

  editAddress(address: Address){
    if(address.isEditing){
      address.isEditing = false;
      this.uiEditMode = false;
    }
    else if(!address.isEditing && !this.uiEditMode){
      address.isEditing = true;
      this.uiEditMode = true;
      this.addressToBeEdited = Object.assign(new Address(), address);
    }
    this.ngOnInit();
  }

  addressCreationModeToggle(){
    if(this.uiCreationMode){
      this.uiCreationMode = false;
      this.uiEditMode = false;
    }
    else{
      this.uiCreationMode = true;
      this.uiEditMode = true;
      this.addressToBeEdited = new Address();
    }
  }

  applyAddressChange(address: Address){
    const idx: number = this.user.addresses.findIndex(userAddress => userAddress.id === address.id);
    if(idx == -1){
      return;
    }
    if(!this.addressEquals(address, this.addressToBeEdited)){
      this.addressService.update(this.addressToBeEdited.id, this.addressToBeEdited, this.user.id);
      this.user.addresses[idx] = this.addressToBeEdited;
      this.editAddress(address);
      this.ngOnInit();
    }
  }

  deleteAddress(address: Address){
    const idx: number = this.user.addresses.findIndex(userAddress => userAddress.id === address.id);
    this.user.addresses.splice(idx, 1);
    this.addressService.delete(address.id, this.user.id);
  }

  createAddress(address: Address){
    this.addressService.create(address, this.user.id).subscribe();
    this.user.addresses.push(address);
  }

  isOwnUserPage(): boolean{
    return this.currentUser.id === this.user.id;
  }

  trackByIndex(index: number, obj: any): any{
    return index;
  }

  updateUser(){
    this.userService.update(this.user.id, this.user).subscribe(resp => {
      if(resp.status != 200){
        return;
      }

      if(this.isEmailChanged()){
        this.userService.logout();
      }
      
      sessionStorage.setItem("current-user", JSON.stringify(User.convertNewUser(this.user)));
      sessionStorage.setItem("current-user-email", JSON.stringify(this.user.email));

      this.currentUser = this.user;
    });

  }

  // updateChanged(){
  //   for(let address of this.user.addresses){
  //     for(let origAddress of this.currentUser.addresses){
  //       if(address.id === origAddress.id){
  //         this.addressService.update(address.id, address, this.user.id);
  //       }
  //     }
  //   }
  // }

  addressEquals(addressOriginal: Address, addressNew: Address): boolean{
    return addressOriginal.country === addressNew.country && addressOriginal.zipCode === addressNew.zipCode &&
    addressOriginal.city === addressNew.city && addressOriginal.street === addressNew.street &&
    addressOriginal.houseNum === addressNew.houseNum;
  }

  isEmailChanged(): boolean{
    return this.user.email !== this.currentUser.email;
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

  onFileNameChanged(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file.name;
    }
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        const arrayBuffer: ArrayBuffer = e.target.result;
        const byteArray = new Uint8Array(arrayBuffer);
        this.user.profilePicture = Array.from(byteArray);
      };

      reader.readAsArrayBuffer(file);
    }
  }

  removeImage(){
    this.user.profilePicture = null;
    this.user.imgSrc = '';
  }
}
