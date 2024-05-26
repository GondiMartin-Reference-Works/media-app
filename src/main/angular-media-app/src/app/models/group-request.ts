import { Group } from "./group";
import { User } from "./user";

export class GroupRequest {
    id: number;
    group: Group | null;
    senderUser: User | null;

    constructor(){
        this.id = 0;
        this.group = null;
        this.senderUser = null;
    }
}
