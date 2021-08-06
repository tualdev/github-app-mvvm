

package com.undabot.githubapp.mapper

import com.undabot.githubapp.model.MovieErrorResponse
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

/**
 * A mapper for mapping [ApiResponse.Failure.Error] response as custom [MovieErrorResponse] instance.
 *
 * @see [ApiErrorModelMapper](https://github.com/undabot/sandwich#apierrormodelmapper)
 */
object ErrorResponseMapper : ApiErrorModelMapper<MovieErrorResponse> {

  /**
   * maps the [ApiResponse.Failure.Error] to the [MovieErrorResponse] using the mapper.
   *
   * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
   * @return A customized [MovieErrorResponse] error response.
   */
  override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): MovieErrorResponse {
    return MovieErrorResponse(apiErrorResponse.statusCode.code.toString(), apiErrorResponse.message())
  }
}
