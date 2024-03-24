import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../main-page/services/auth.service';

export const authGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  authService.checkLoging();

  if (authService.isLoggedIn){
    return true;
  }

  return router.parseUrl('/auth/login');
};
