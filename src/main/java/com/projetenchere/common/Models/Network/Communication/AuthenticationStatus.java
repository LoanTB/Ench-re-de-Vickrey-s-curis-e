package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Identity;

public record AuthenticationStatus(String status, Identity authorOfSignature) {
}
