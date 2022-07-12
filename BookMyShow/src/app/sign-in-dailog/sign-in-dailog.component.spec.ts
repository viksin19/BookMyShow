import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignInDailogComponent } from './sign-in-dailog.component';

describe('SignInDailogComponent', () => {
  let component: SignInDailogComponent;
  let fixture: ComponentFixture<SignInDailogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignInDailogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignInDailogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
