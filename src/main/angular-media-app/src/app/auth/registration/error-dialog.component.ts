import { Component } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";



@Component({
    selector: 'app-error-dialog',
    template: `
        <h1 mat-dialog-title>Error</h1>
        <div mat-dialog-content>
        <p>Email already exists. Please choose another one.</p>
        </div>
        <div mat-dialog-actions>
        <button (click)="onOkPressed()" mat-button mat-dialog-close>OK</button>
        </div>
        `
    })export class ErrorDialogComponent{
        constructor(
            private dialogRef: MatDialogRef<ErrorDialogComponent>
        ){}

        onOkPressed(){
            this.dialogRef.close();
        }
}


@Component({
    selector: 'app-error-dialog',
    template: `
    <h1 mat-dialog-title>Error</h1>
    <div mat-dialog-content>
        <p>Email or password is incorrect.</p>
    </div>
    <div mat-dialog-actions>
        <button (click)="onOkPressed()" mat-button mat-dialog-close>OK</button>
    </div>
    `
    })export class ErrorLoginComponent{
        constructor(
            private dialogRef: MatDialogRef<ErrorDialogComponent>
        ){}

        onOkPressed(){
            this.dialogRef.close();
        }
}
