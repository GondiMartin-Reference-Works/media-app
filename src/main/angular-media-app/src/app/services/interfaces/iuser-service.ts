import { Observable } from "rxjs";
import { RegisterUser } from "../../models/register-user";

export interface IUserService {

    getAll(): Observable<RegisterUser[]>;
    
    create(newUser: RegisterUser): Observable<RegisterUser>;
}
