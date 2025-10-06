import AuthenticationModel from "./AuthenticationModel.js"

export default class AuthenticationController {
    static confirmAuth() {
        AuthenticationModel.setAuthState({
            state: true
        })
    }

    static discardAuth() {
        AuthenticationModel.setAuthState({
            state: false
        })
    }

    static checkAuth() {

        return AuthenticationModel.getAuth()
    }
}