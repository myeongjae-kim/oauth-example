import { Inter } from 'next/font/google';
import { GoogleOAuthProvider, GoogleLogin, CredentialResponse } from '@react-oauth/google';
import { useState } from 'react';
import axios from 'axios';

const inter = Inter({ subsets: ['latin'] });

const useProfileFromBackend = (token?: string): object | undefined => {
  const [profile, setProfile] = useState<object>();

  if (token) {
    axios
      .get('http://localhost:8080/profile', { headers: { Authorization: 'Bearer ' + token } })
      .then((response) => {
        setProfile(response.data);
      });
  }

  return profile;
};

export default function Home() {
  const [credentialResponse, setCredentialResponse] = useState<CredentialResponse>();
  const profile = useProfileFromBackend(credentialResponse?.credential);

  return (
    <main
      className={`flex min-h-screen flex-col items-center justify-between p-24 ${inter.className}`}
    >
      <GoogleOAuthProvider
        clientId={'456032865148-5siju890rqfsjs2hph4bs9lcis1e5ak7.apps.googleusercontent.com'}
      >
        <GoogleLogin
          onSuccess={(credentialResponse) => {
            console.log(credentialResponse);
            setCredentialResponse(credentialResponse);
          }}
          onError={() => {
            console.log('Login Failed');
          }}
          auto_select={true}
        />
      </GoogleOAuthProvider>
      <div>
        <h2>credential responses</h2>
        <pre className={'w-screen p-4'}>{JSON.stringify(credentialResponse, null, 2)}</pre>
      </div>
      <div>
        <h2>profile from backend</h2>
        <pre className={'w-screen p-4'}>{JSON.stringify(profile, null, 2)}</pre>
      </div>
    </main>
  );
}
