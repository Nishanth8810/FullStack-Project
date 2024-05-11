import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export  function passwordLengthValidator(minLength: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.value as string;

    if (password && password.length < minLength) {
      return { passwordLength: true };
    }
    return null;
  };
}
