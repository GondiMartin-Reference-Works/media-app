import { GroupRequest } from "./group-request";
import { Post } from "./post";
import { User } from "./user";

export class Group {
    id: number;
    name: string;
    description: string;
    image: number[] | null;
    adminUser: User | null;
    participantUsers: User[];
    groupRequests: GroupRequest[];
    posts: Post[];
    imgSrc: string;

    constructor(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.image = null;
        this.adminUser = null;
        this.participantUsers = [];
        this.groupRequests = [];
        this.posts = [];
        this.imgSrc = "";
    }

    static convertNewGroup(group: Group): Group{
        let newGroup: Group = new Group();
        newGroup.id = group.id;
        newGroup.name = group.name;
        newGroup.description = group.description;
        newGroup.image = group.image;
        newGroup.adminUser = group.adminUser;
        newGroup.participantUsers = group.participantUsers;
        newGroup.groupRequests = group.groupRequests;
        newGroup.posts = group.posts;
        newGroup.imgSrc = group.getImageSrc();
        return newGroup;
    }

    getImageSrc(): string{
        if(this.image){
            return 'data:image/jpeg;base64,' + this.image;
        }
        return '';
    }
}
