document.addEventListener('DOMContentLoaded', function() {
    var logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            fetch('/api/auth/logout', {
                method: 'GET',
                credentials: 'include'
            })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/';
                } else {
                    alert('로그아웃 에러');
                }
            })
            .catch(error => console.error('Error logging out:', error));
        });
    }
});
