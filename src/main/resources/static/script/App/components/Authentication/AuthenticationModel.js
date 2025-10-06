import { LOCAL_STORAGE_KEYS } from "../../constants/localStorageKeys.js"

export default class AuthenticationModel {
    static isAuthenticated = null

    static setAuthState(
        {
            state
        }
    ) {

        if (state) {
            localStorage.setItem(LOCAL_STORAGE_KEYS.IS_AUTHENTICATED, 'true')
            this.isAuthenticated = true
        } else {
            localStorage.removeItem(LOCAL_STORAGE_KEYS.IS_AUTHENTICATED)
            this.isAuthenticated = false
        }
    }

    static getAuth() {
        return localStorage.getItem(LOCAL_STORAGE_KEYS.IS_AUTHENTICATED)
    }
}