import { Component, OnInit } from '@angular/core';
import { Group } from '../models/group';
import { User } from '../models/user';
import { GroupService } from '../services/group.service';

@Component({
  selector: 'app-groups-page',
  templateUrl: './groups-page.component.html',
  styleUrl: './groups-page.component.css'
})
export class GroupsPageComponent implements OnInit {

  groups: Group[] = [];
  createFormButton: boolean;
  newGroup: Group = new Group();
  selectedFile: string = "";
  exImg: string = "https://www.bankrate.com/2019/03/22142110/How-to-trade-stocks.jpg?auto=webp&optimize=high&width=912";
  formTitleText: string = "New group";
  formButtonText: string = "Submit";
  isCreating: boolean;

  constructor(
    private groupService: GroupService
  ) {
    this.createFormButton = false;
    this.isCreating = true;
    this.loadGroups();
  }

  ngOnInit(): void {
  }

  loadGroups() {
    let user: User = JSON.parse(sessionStorage.getItem("current-user") ?? "");
    this.groupService.getAllByUserId(user.id).subscribe(groups => {
      this.groups = groups.map(groupData => {
        let group = Object.assign(new Group(), groupData);
        group.imgSrc = group.getImageSrc();
        return group;
      })
    });
  }

  createForm() {
    this.createFormButton = true;
    this.isCreating = true;
    this.newGroup = new Group();
  }

  sendForm() {

    if (this.isCreating) {
      // Creating
      this.newGroup.adminUser = JSON.parse(sessionStorage.getItem("current-user") ?? "");
      this.groupService.create(this.newGroup).subscribe(newGroup => {
        this.createFormButton = false;
        this.isCreating = true;
        this.selectedFile = "";
        let ng: Group = Group.convertNewGroup(newGroup);
        this.groups.unshift(ng);
        this.ngOnInit();
      });
    } else {
      // Updating
      this.groupService.update(this.newGroup).subscribe(newGroup => {
        this.createFormButton = false;
        this.isCreating = true;
        this.selectedFile = "";
        let ng: Group = Group.convertNewGroup(newGroup);
        this.groups.unshift(ng);
        this.ngOnInit();
      });
    }
  }

  goToGroupPage(arg0: number) {
    throw new Error('Method not implemented.');
  }

  onFileNameChanged(event: any): void {
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
        this.newGroup.image = Array.from(byteArray);
      };

      reader.readAsArrayBuffer(file);
    }
  }

  closeForm() {
    this.createFormButton = false;
    this.isCreating = true;
    this.formTitleText = "New group";
    this.formButtonText = "Submit";
    this.newGroup = new Group();
    this.selectedFile = "";
  }

  isJoinedByUser(group: Group): boolean {
    let user: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    let joinedUser: User | undefined = group.participantUsers.find(u => u.id == user.id);
    return joinedUser == undefined ? false : true;
  }

  editGroup(editableGroup: Group): void {
    this.createFormButton = true;
    this.isCreating = false;
    this.formTitleText = "Update group";
    this.formButtonText = "Update";
    this.newGroup = Object.assign(new Group(), editableGroup);
    console.log(this.newGroup.name + this.newGroup.description);

    this.ngOnInit();
  }

  deleteGroup(groupId: number): void {
    this.groupService.delete(groupId).subscribe(_ => {
      const groupIndex = this.groups.findIndex(g => g.id === groupId);
      this.groups.splice(groupIndex, 1);
      this.ngOnInit();
    });
  }

  isMyGroup(group: Group): boolean {
    let user: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    return group.adminUser?.id == user.id;
  }
}
