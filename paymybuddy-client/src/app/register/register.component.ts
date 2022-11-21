import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {User} from '../model/user';


@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    form!: FormGroup;
    user = new User();

    constructor(
        private httpClient: HttpClient,
        private router: Router
    ) {

    }

    ngOnInit(): void {
    }

    onSubmit(form: any) {
        console.log(form.value);
        this.httpClient.post('http://localhost:8080/register', this.form.value)
            .subscribe(() => {
                this.router.navigate(['/login']);
            });
        console.log("Formulaire inscription envoyé");
    }

    // onSubmit() {
    //   this.httpClient.post('http://localhost:8080/register', this.form.getRawValue())
    //     .subscribe( () => {
    //         this.router.navigate(['/login']);
    //     });
    //     console.log("Formulaire inscription envoyé");
    // }

    // validate(){
    //   var form = document.getElementsByClassName('needs-validation')[0] as HTMLFormElement;
    //   if (form.checkValidity() === false) {
    //     event.preventDefault();
    //     event.stopPropagation();
    //   }
    //   form.classList.add('was-validated');
    // }

}
