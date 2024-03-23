import { Observable } from "rxjs";
import { RegisterUser } from "../../../models/register-user";
import { LoginUser } from "../../../models/login-user";

export interface IUserService {
    
    create(newUser: RegisterUser): Observable<string>;

    login(user: LoginUser): Observable<string>;
}
