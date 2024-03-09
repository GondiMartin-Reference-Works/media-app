import { Observable } from "rxjs";
import { RegisterUser } from "../../models/register-user";

export interface IUserService {
    
    create(newUser: RegisterUser): Observable<RegisterUser>;
}
