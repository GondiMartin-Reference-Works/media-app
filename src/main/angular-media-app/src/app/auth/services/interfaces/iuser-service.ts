import { Observable } from "rxjs";
import { RegisterUser } from "../../../models/register-user";
import { LoginUser } from "../../../models/login-user";
import { AuthResponse } from "../../../models/auth-response";

export interface IUserService {
    
    create(newUser: RegisterUser): Observable<AuthResponse>;

    login(user: LoginUser): Observable<AuthResponse>;
}
