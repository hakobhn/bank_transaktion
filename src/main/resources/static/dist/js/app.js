
const APP_CONSTANTS = {
    unknown: "unknown"
}

function alertError(xhr) {
    var title = "Request failed";
    var subtitle = "400 Bad Request";
    var message = "Server returns error";
    try {
        // console.log("responseText: " + JSON.stringify(xhr.responseText));
        var response = JSON.parse(xhr.responseText);

        title = response.title;
        message = response.message;
        if (response.code == 400) {
            subtitle = "400 Bad Request";
        } else if (response.code == 401) {
            subtitle = "401 Unauthorized";
        } else if (response.code == 403) {
            subtitle = "403 Forbidden";
        } else if (response.code == 404) {
            subtitle = "404 Not Found";
        } else if (response.code == 406) {
            subtitle = "406 Not Acceptable";
        } else if (response.code == 500) {
            subtitle = "500 Internal Server Error";
            title = "We will work on fixing that right away";
        }
    } catch (e) { }
    showError(title, subtitle, message);
}

function showError(title, subtitle, message) {
    showMessage('bg-danger', title, subtitle, message);
}

function showSuccess(title, subtitle, message) {
    showMessage('bg-success', title, subtitle, message);
}

function showMessage(bgClass, title, subtitle, message) {
    $(document).Toasts('create', {
        class: bgClass,
        title: title,
        position: 'topRight',
        autohide: true,
        delay: 10000,
        subtitle: subtitle,
        body: message
    });
    return false;
}

const APP_MONTH_NAMES = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
];


function getFormattedDate(date, prefomattedDate = false, hideYear = false) {
    const day = date.getDate();
    const month = APP_MONTH_NAMES[date.getMonth()];
    const year = date.getFullYear();
    const hours = date.getHours();
    let minutes = date.getMinutes();

    if (minutes < 10) {
        // Adding leading zero to minutes
        minutes = `0${minutes}`;
    }

    if (prefomattedDate) {
        // Today at 10:20
        // Yesterday at 10:20
        return `${prefomattedDate} at ${hours}:${minutes}`;
    }

    if (hideYear) {
        // 10. January at 10:20
        return `${day} ${month} at ${hours}:${minutes}`;
    }

    // 10. January 2017. at 10:20
    return `${day}. ${month} ${year}. at ${hours}:${minutes}`;
}


// --- Main function
function timeAgo(dateParam) {
    if (!dateParam) {
        return null;
    }

    const date = typeof dateParam === 'object' ? dateParam : new Date(dateParam);
    const DAY_IN_MS = 86400000; // 24 * 60 * 60 * 1000
    const today = new Date();
    const yesterday = new Date(today - DAY_IN_MS);
    const seconds = Math.round((today - date) / 1000);
    const minutes = Math.round(seconds / 60);
    const isToday = today.toDateString() === date.toDateString();
    const isYesterday = yesterday.toDateString() === date.toDateString();
    const isThisYear = today.getFullYear() === date.getFullYear();


    if (seconds < 5) {
        return 'now';
    } else if (seconds < 60) {
        return `${seconds} seconds ago`;
    } else if (seconds < 90) {
        return 'about a minute ago';
    } else if (minutes < 60) {
        return `${minutes} minutes ago`;
    } else if (isToday) {
        return getFormattedDate(date, 'Today'); // Today at 10:20
    } else if (isYesterday) {
        return getFormattedDate(date, 'Yesterday'); // Yesterday at 10:20
    } else if (isThisYear) {
        return getFormattedDate(date, false, true); // 10. January at 10:20
    }

    return getFormattedDate(date); // 10. January 2017. at 10:20
}

function showDateDiff(start, end) {
    // make checks to make sure a and b are not null
    // and that they are date | integers types

    var diffMs = (end - start); // milliseconds between now & Christmas
    var diffDays = Math.floor(diffMs / 86400000); // days
    var diffHrs = Math.floor((diffMs % 86400000) / 3600000); // hours
    var diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000); // minutes
    var diffSecs = Math.round((((diffMs % 86400000) % 3600000) % 60000) / 1000); // seconds

    var result = "";
    if (diffDays > 0) {
        result += diffDays + " days ";
    }
    if (diffHrs > 0) {
        result += diffHrs + " hrs ";
    }
    if (diffMins > 0) {
        result += diffMins + " mins ";
    }
    if (diffSecs > 0) {
        result += diffSecs + " secs";
    }

    return result.trim();
}

// Cookies
function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else var expires = "";

    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}

function cleanText(str) {
    return str.replace(/<\/?[^>]+(>|$)/g, "");
}

// Handle session expired error.
$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    // console.log("ajax error json: "+JSON.stringify(jqxhr));
    if (jqxhr.responseText.indexOf("<title>Bank Admin | Log in</title>") !== -1) {
        document.location.href = '/';
    }
});

function printAccount(account, performDate) {
    let result = '<div class="user-block">\n' +
        '      <img class="img-circle img-bordered-sm" src="' + account['avatar'] + '" alt="' + account['email'] + '">\n' +
        '      <span class="username">\n' +
        '          <a href="';
    result += account['uuid'] == 'unknown'?'javascript: void(0);':'/accounts/profile/'+account['uuid'];
    result += '">' + cleanText(account['firstName'] + ' ' + account['lastName']) +
        '</a>\n'+
        '      </span>\n';
    if (performDate != null && performDate != undefined) {
        result += '<span class="description text-bold">Performed ' + timeAgo(performDate) + '</span>\n';
    }
    result +='   </div>';
    return result;
}